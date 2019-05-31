package com.example.demo.controller;


import com.example.demo.exaption.NotfoundExcaption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private  List<Map<String,String>> messages = new ArrayList<Map<String,String>>()
    {
        {
        add(new HashMap<String, String>(){
            {
                put("id","1");
                put("text","First message");
            }
        });
        add(new HashMap<String, String>(){
            {
                put("id","2");
                put("text","Second message");
            }
        });
        add(new HashMap<String, String>(){
            {
                put("id","3");
                put("text","Third message");
            }
        });

        }
    };
    @GetMapping
    public List<Map<String,String>>  list(){
        return messages;
    }
    @GetMapping("{id}")
    public Map<String,String> getOne(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream().filter(message -> message.get("id").equals(id)).findFirst().orElseThrow(NotfoundExcaption::new);
    }

    @PostMapping
    public Map<String,String> create(@RequestBody Map<String,String> messages) {
        messages.put("id",String.valueOf(counter++));
        this.messages.add(messages);
        return messages;
    }

    @PutMapping("{id}")
    public Map <String,String> update(@PathVariable String id,@RequestBody Map<String,String> messages) {
        Map<String, String> messagefromDB = getMessage(id);
        messagefromDB.putAll(messages);
        messagefromDB.put("id",id);
        return messagefromDB;
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable  String id) {
        Map<String, String> message = getMessage(id);
            messages.remove(message);
    }
}
