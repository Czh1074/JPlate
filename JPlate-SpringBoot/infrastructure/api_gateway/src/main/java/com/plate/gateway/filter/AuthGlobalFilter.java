package com.plate.gateway.filter;

import com.google.gson.JsonObject;
import com.plate.commonutils.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 *
 * @author qy
 * @since 2022-5-17
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    static String[][] whiteList ={
        {"POST", "/userService/user/login"},
        {"POST", "/userService/user/register"},
    };

    static String[][] urlFilters ={
        {"POST", "/noticeService/notice/getNoticeInfoById/*"},
        {"POST", "/userService/user/**"},
        {"POST", "/trendService/trend/getTrendInfoById/**"},
        {"POST", "/trendService/trend/addTrend"},
        {"POST", "/msgService/message/getMessageInfoById/**"},
        {"POST", "/msgService/message/addMessage"},
        {"POST", "/goodService/good/getGoodInfoById/**"},
        {"POST", "/goodService/good/addGood"},
    };

    private boolean matchUrl(String method, String path) {
        // TODO: 使用字典树优化性能
        for (String[] filter : whiteList) {
            if(filter[0].equals(method) && antPathMatcher.match(filter[1], path)) {
                return false;
            }
        }
        for (String[] filter : urlFilters) {
            if(filter[0].equals(method) && antPathMatcher.match(filter[1], path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().toString();

        //校验用户必须登录
//        System.out.println("filter-->"+path+"-"+method);
        if(matchUrl(method, path)) {

            List<String> tokenList = request.getHeaders().get("token");
//            System.out.println("filter-->"+path+"-"+method+"-->matched,token:"+tokenList);
            if(null == tokenList) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            } else {
                boolean isCheck = checkToken(tokenList.get(0));
                if(!isCheck) {
                    ServerHttpResponse response = exchange.getResponse();
                    return out(response);
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "鉴权失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥进行加密操作

    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
