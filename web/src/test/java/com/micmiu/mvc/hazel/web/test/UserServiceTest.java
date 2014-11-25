package com.micmiu.mvc.hazel.web.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.micmiu.mvc.hazel.web.service.RoleService;
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

import com.micmiu.mvc.hazel.web.entity.User;
import com.micmiu.mvc.hazel.web.service.BlogService;
import com.micmiu.mvc.hazel.web.service.MenuService;
import com.micmiu.mvc.hazel.web.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	AtomicInteger atom = new AtomicInteger();

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private BlogService blogService;

	@Test
	public void testCreate() {

		int beforeDbCount = userService.queryAll().size();

		userService.create(genRandomUser());

		int afterDbCount = userService.queryAll().size();

		Assert.assertEquals(beforeDbCount + 1, afterDbCount);
	}

	@Test
	public void testUpdate() {

		User user = genRandomUser();
		Long ID = userService.create(user);
		String expectedPassword = "123234";
		user.setPassword(expectedPassword);
		userService.update(user);

		String actualPassword = userService.find(ID).getPassword();

		Assert.assertEquals(expectedPassword, actualPassword);

	}

	@Test
	public void testDelete() {

		int beforeDbCount = userService.queryAll().size();

		Long id = userService.create(genRandomUser());

		userService.delete(id);

		int afterDbCount = userService.queryAll().size();

		Assert.assertEquals(beforeDbCount, afterDbCount);
	}

	@Test
	public void testList() {
		User user = genRandomUser();
		userService.create(user);
		List<User> userList = userService.queryAll();

		MatcherAssert.assertThat(userList, Matchers.hasItem(user));
	}

	public User genRandomUser() {
		long randomKey = System.nanoTime() + atom.addAndGet(1);
		User user = new User();
		user.setLoginName("Michael" + randomKey);
		user.setEmail("sjsky" + randomKey + "@micmiu.com");
		user.setPassword("micmiu");
		user.setOther("test");
		return user;
	}
}