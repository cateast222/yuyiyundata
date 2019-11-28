package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.yuyiyun.YYdata.core.util.JwtTokenUtil;

@SpringBootTest
class YunYiDataApplicationTests {

	@Test
	void contextLoads() {
	}
	
	public static void main(String[] args) {
		String token = "XAS8wWZqfd3_vJYnX57jYBYdsGT79FLSl0_rUCFJqhHboRg5zZXkM_pTaU_2ZBvYU055IG7M0Pe5Q8jHNM5XnSAfvTGuLtdboQYnMswVVFc41aYAnyvPGpiwNnWN0G8qIGSurAbQxttYfUnjOwkQaAjkLibuc2J4Jkfo4CpYn9ZqwC6KTP0j1VXenABG-Zsws6m70IlhMRF9ipoi8NFVWdQqdmDkppBn7ah4lcarNdFv14K0y6UbXswVaCWO0qzFOVXMMrxQymV63RIfSsURsf9rlTEp6H_4JBImUQ4_XX9MeHsfzuBwAREHolcUq6iVbi-q_e2NeFEcYMwtHxQFbBOFLhV61gO_L-atyiXvymzsykci7VRQC4w_i4t6qrnFf6drABJ7Ic7djgpQbjIqCXjp4VoYCxNdbV8TKSRRNcewFU0yc3wfVYH3eQUQhglZ_8zp5UF0V89RJ93EjPSk3Z8-UWA63Agvyvj2kkMxoU4";
		System.out.println(JwtTokenUtil.getUsernameFromToken(token));
	}

}
