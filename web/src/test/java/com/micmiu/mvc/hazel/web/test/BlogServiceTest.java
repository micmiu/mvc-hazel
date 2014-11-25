package com.micmiu.mvc.hazel.web.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.micmiu.mvc.hazel.web.entity.Blog;
import com.micmiu.mvc.hazel.web.service.BlogService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BlogServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	AtomicInteger atom = new AtomicInteger();

	@Autowired
	private BlogService blogService;

	@Test
	public void testCreate() {

		int beforeDbCount = blogService.queryAll().size();

		blogService.create(genRandom());

		int afterDbCount = blogService.queryAll().size();

		Assert.assertEquals(beforeDbCount + 1, afterDbCount);
	}

	@Test
	public void testUpdate() {

		Blog blog = genRandom();
		Long ID = blogService.create(blog);
		String expected = "michael";
		blog.setAuthor(expected);
		blogService.update(blog);

		String actual = blogService.find(ID).getAuthor();

		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testDelete() {

		int beforeDbCount = blogService.queryAll().size();

		Long id = blogService.create(genRandom());

		blogService.delete(id);

		int afterDbCount = blogService.queryAll().size();

		Assert.assertEquals(beforeDbCount, afterDbCount);
	}

	@Test
	public void testList() {
		Blog blog = genRandom();
		blogService.create(blog);
		List<Blog> list = blogService.queryAll();

		MatcherAssert.assertThat(list, Matchers.hasItem(blog));
	}

	public Blog genRandom() {
		long randomKey = System.nanoTime() + atom.addAndGet(1);
		Blog blog = new Blog();
		blog.setAuthor("micmiu");
		blog.setCategory("j2ee");
		blog.setTitle("j2ee demo " + randomKey);
		blog.setUrl("www.micmiu.com");
		return blog;
	}
}