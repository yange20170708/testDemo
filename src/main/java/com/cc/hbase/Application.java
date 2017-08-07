package com.cc.hbase;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cc.hbase.service.RWSplitService;
import com.cc.hbase.service.impl.RWSplitJdbcServiceImpl;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
public class Application{

	@Autowired
	RWSplitService splitService;
	@Autowired
	RWSplitJdbcServiceImpl splitJdbcService;
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		System.gc();
	}
	
	@RequestMapping("/")
    public ModelAndView index() {
		return new ModelAndView("redirect:/swagger-ui.html");
    }

	@ApiOperation(value="home")
	@RequestMapping(value = "/home", method = RequestMethod.POST)
    @ResponseBody
    public String home(@RequestBody String a) {
        return "Hello World! "+a;
    }

	@ApiOperation(value="readWriteTemplettest")
	@RequestMapping(value = "/readWriteTemplettest", method = RequestMethod.GET)
    @ResponseBody
    public String readWriteTemplettest() {
		splitService.readWriteTX();
		splitService.readTX();
		splitService.readReadonlyTX();
		splitService.read();
		splitService.readReadOnly();
        return "readWriteTemplettest执行完毕";
    }
	@ApiOperation(value="readWriteJdbctest")
	@RequestMapping(value = "/readWriteJdbctest", method = RequestMethod.GET)
    @ResponseBody
    public String readWriteJdbctest() {
		splitJdbcService.readWriteTest();
        return "readWriteJdbctest执行完毕";
    }
}
