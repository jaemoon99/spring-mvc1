package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age{}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // required = true = 필수값
            @RequestParam(required = false) Integer age) { // required = false = 없어도 됨
            /*
            * int 자료형에서 required = false 를 사용할 경우 기본값이 null이기 때문에 오류가 발생함
            오류를 없애기 위해서는 int age가 아닌 Integer age를 사용해야됨

            * String의 경우 ""(공백)을 입력해도 통과됨 (주의)
            */
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, // required = true = 필수값
            @RequestParam(required = false, defaultValue = "-1") int age) { // required = false = 없어도 됨
            /*
            * int 자료형에서 required = false 를 사용할 경우 기본값이 null이기 때문에 오류가 발생함
            오류를 없애기 위해서는 int age가 아닌 Integer age를 사용해야됨 -> defaultValue를 사용하면 int age 사용 가능

            * String의 경우 ""(공백)을 입력해도 통과됨 (주의) -> defaultValue를 사용해 예외처리 가능
             */
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // RestController가 아니더라도 폼을 찾지 않고 http응답바디에 리턴값을 넣어줌
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        /*
         * 파라미터의 값이 여러개일 경우 -> ?username=id1&username=id2
         * Map이 아닌 MultiValueMap을 사용
         */
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) { // RequestParam 처럼 annotation 생략 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        return "ok";
    }
}
