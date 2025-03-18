package dev.boot.mvc;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MusicblogApplicationTests {

	@Autowired
	private CateDAOInter cateDAOInter;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreate() {
		CategoryVO cateVO = new CategoryVO();

		cateVO.setId(1);
		cateVO.setTitle("TOO BAD");
		cateVO.setArtist("G-DRAGON");
		cateVO.setGenre("Pop");
		cateVO.setMood("Funky");
		cateVO.setEra("2020s");
		cateVO.setRegion("Korea");
		cateVO.setPurpose("Party");
		cateVO.setVisible("Y");
		cateVO.setRdate("2025-03-18");

		int cnt = this.cateDAOInter.create(cateVO);
		System.out.println("-> cnt: " + cnt);


	}


}
