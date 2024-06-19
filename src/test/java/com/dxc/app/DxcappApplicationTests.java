package com.dxc.app;

import com.dxc.app.batch.config.BatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DxcappApplicationTests {

	@Test
	void contextLoads() {
		assertNotNull(BatchConfig.class);
	}

}
