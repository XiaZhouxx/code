package com.code;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xz
 * @date 2021/11/4 15:23
 */
@RestController
public class GatewayController {
    @Autowired
    SpringClientFactory clientFactory;

    @Autowired
    InMemoryRouteDefinitionRepository repository;

    @Autowired
    GatewayProperties properties;

    @RequestMapping("/test/{name}")
    public String test(@PathVariable String name) throws URISyntaxException {

        /*
         * 基于SpringClientFactory实现动态负载均衡策略配置.
         * Ribbon是将不同服务的配置存在SpringClientFactory中, 每个服务构建的Spring容器来进行隔离
         * 所以基于此Factory, 对于每个服务粒度进行动态的负载均衡策略配置。
         */
        final ILoadBalancer loadBalancer = clientFactory.getLoadBalancer(name);
        BaseLoadBalancer lb = (BaseLoadBalancer) loadBalancer;
        lb.setRule(new RoundRobinRule());

        // 实现动态路由配置
        RouteDefinition definition = new RouteDefinition();
        // 路由匹配和过滤器的配置
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        Map<String, String> map = new HashMap<>();
        map.put("patterns", "/test/**");
        predicateDefinition.setArgs(map);
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("StripPrefix");
        map = new HashMap<>();
        map.put("parts", "1");
        filterDefinition.setArgs(map);

        definition.setUri(new URI("lb://ALGORITHM-CENTER-JOB-ADMIN"));
        definition.setPredicates(Arrays.asList(predicateDefinition));
        definition.setFilters(Arrays.asList(filterDefinition));

        /*
        * 几种方式实现动态路由
        * 1. 基于GatewayProperties, 基于配置文件的实现, 它会将配置文件中的Routes定义装载在List中, 那么从Spring容器中取出, 直接增加自定义的RouteDefinition即可
        * 2. 基于InMemoryRouteDefinitionRepository, 它是作为一个内存的Route存储数据库, 但是它并不会主动去存储Route, 所以动态save实现动态路由.
        * 3. 借鉴InMemoryRouteDefinitionRepository, 自定义存储, 例如数据库, 可以达到每次的路由落库保存, 基于内存重启则丢失路由信息
         */
        properties.getRoutes().add(definition);
        repository.save(Mono.just(definition));
        // customRepository.save(definition);

        return "success";
    }
}
