package com.hewak.blog.user;


import com.hewak.blog._core.errors.Exception400;
import com.hewak.blog._core.errors.Exception401;
import com.hewak.blog._core.errors.Exception404;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 로그인
    public UserResponse.SessionDTO login(UserRequest.LoginDTO loginDTO){

        log.info("로그인 요청 - 사용자명 : {}",loginDTO.getUsername());
        User userEntity = userRepository.findByUsernameAndPassword(
                loginDTO.getUsername(), loginDTO.getPassword()).orElseThrow(() -> {
                    return new Exception401("사용자명 혹은 비밀번호가 일치하지 않습니다.");
        });

        log.info("로그인 성공 - 사용자명 : {}",loginDTO.getUsername());
        return new UserResponse.SessionDTO(userEntity);
    } // end of login

    // 회원가입
    @Transactional
    public UserResponse.JoinDTO Join(UserRequest.JoinDTO joinDTO){

        log.info(" 회원가입 요청 ");
        userRepository.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
            log.warn("회원가입 실패 - 중복된 사용자명 : {}",joinDTO.getUsername());
            throw new Exception400("사용중인 이름입니다.");
        });
        User user = joinDTO.toEntity();
        User savedUserEntity = userRepository.save(user);
        log.info("회원 가입 서비스 완료 - id : {}",savedUserEntity.getId());

        return new UserResponse.JoinDTO(savedUserEntity);
    } // end of join

    // 회원정보조회
    @Transactional
    public UserResponse.JoinDTO findByUser(Integer id){
        log.info("회원 정보 조회 요청 ");
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            log.info("회원 정보 조회 실패");
            return new Exception404("사용자 조회 실패");
        });

        return new UserResponse.JoinDTO(userEntity);
    } // end of findByUser

    //사용자정보수정
    @Transactional
    public UserResponse.SessionDTO update(Integer id,
                                       UserRequest.UpdateDTO updateDTO,
                                       HttpSession session){

        log.info("회원 정보 수정 요청 ");
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            log.info("회원 정보 조회 실패");
            return new Exception404("정보 조회 실패");
        });

        userEntity.update(updateDTO);
        log.info("회원 정보 수정 완료");

        UserResponse.SessionDTO sessionDTO = new UserResponse.SessionDTO(userEntity);

        // 세션 동기화
        session.setAttribute("sessionUser",sessionDTO);
        return sessionDTO;
    } // end of updateDTO


} // end of class
