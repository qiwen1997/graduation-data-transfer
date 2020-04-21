package com.einvoice;

import com.einvoice.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {

  @Autowired
  private JsonUtil jsonUtil;

  @Test
  public void getMaxTimeTest(){
    System.out.println(jsonUtil.getMaxdate());
  }

  @Test
  public void setMaxIDTest(){
   // jsonUtil.setMaxID("4");
  }
}
