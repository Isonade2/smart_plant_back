package wku.smartplant.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wku.smartplant.domain.Member;
import wku.smartplant.service.MemberService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("dofilter 실행");

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.split(" ")[1];
        System.out.println("token = " + token);

        try {
            if (JwtTokenUtil.isExpired(token)) {
            }

        } catch (JwtTokenUtil.TokenValidationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        }



        //jwt token에서 memberId 추출
        String memberIdStr = JwtTokenUtil.getMemberId(token);

        Long memberId = Long.parseLong(memberIdStr);

        Member loginMember = memberService.findMemberById(memberId);

        // loginMember 로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginMember.getId(), null, List.of(new SimpleGrantedAuthority(loginMember.getRole()))
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
