package top.simba1949.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author simba1949@outlook.com
 * @date 2018/10/14 9:58
 */
@RestController
public class HelloWorldController {

    @GetMapping("/say")
    public String sayHello(){
        return "Hello World!!!";
    }

    @GetMapping("/return/{name}")
    public String returnName(@PathVariable("name")String name){
        return name;
    }

    @GetMapping("/list")
    public List<String> stringList(){
        List<String> list = new ArrayList<>();
        list.add("李白");
        list.add("杜甫");
        list.add("白居易");
        list.add("苏轼");
        return list;
    }
}
