package com.ips.hospitalmanagementsystem.security;
import com.ips.hospitalmanagementsystem.dto.LoginRequestDto;
import com.ips.hospitalmanagementsystem.dto.LoginResponseDto;
import com.ips.hospitalmanagementsystem.dto.SignupRequestDto;
import com.ips.hospitalmanagementsystem.dto.SignupResponseDto;
import com.ips.hospitalmanagementsystem.entity.Patient;
import com.ips.hospitalmanagementsystem.entity.User;
import com.ips.hospitalmanagementsystem.entity.type.AuthProviderType;
import com.ips.hospitalmanagementsystem.entity.type.RoleType;
import com.ips.hospitalmanagementsystem.repository.PatientRepository;
import com.ips.hospitalmanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import javax.management.relation.RoleResult;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final AuthenticationManager authenticationManager;
    private  final AuthUtil authUtil;
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public  LoginResponseDto login(LoginRequestDto loginRequestDto) {
 Authentication authentication= authenticationManager.authenticate(
         new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
 );
        User user = (User) (authentication).getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }
    public User signUpInternal(SignupRequestDto signupRequestDto,
                               AuthProviderType authProviderType,
                               String providerId) {

        User user = userRepository
                .findByUsername(signupRequestDto.getUsername())
                .orElse(null);

        if (user != null)
            throw new IllegalArgumentException("User already exists");

        user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(signupRequestDto.getRoles())
                .build();

        if (authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(
                    passwordEncoder.encode(signupRequestDto.getPassword())
            );
        }
        userRepository.save(user);
        Patient patient =  Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
      patientRepository.save(patient);
        return user;
    }


    // login controller
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = signUpInternal(
                signupRequestDto,
                AuthProviderType.EMAIL,
                null
        );

        return new SignupResponseDto(
                user.getId(),
                user.getUsername()
        );
    }


    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(
            OAuth2User oAuth2User,
            String registrationId
    ) {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);

        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");
String name =oAuth2User.getAttribute("name");
        User emailUser = userRepository
                .findByUsername(email)
                .orElse(null);

        if (user == null && emailUser == null) {

            // signup flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

            user = signUpInternal(
                    new SignupRequestDto (username, null, name ,Set.of(RoleType.PATIENT)),
                    providerType,
                    providerId
            );

        } else if (user != null) {

            if (email != null
                    && !email.isBlank()
                    && email.equals(user.getUsername())) {

                user.setUsername(email);
                userRepository.save(user);
            }

        } else {

            throw new BadCredentialsException(
                    "This email is already registered with provider " + email
            );
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

        return ResponseEntity.ok(loginResponseDto);

    }



}

