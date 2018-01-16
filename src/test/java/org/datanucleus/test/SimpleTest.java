package org.datanucleus.test;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Type;

import mydomain.model.Person;
import mydomain.model.Setting;
import org.datanucleus.util.NucleusLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class SimpleTest {

  private static EntityManagerFactory entityManagerFactory;
  @Rule
  public TestName testNameRule = new TestName();
  private EntityManager entityManager;

  @BeforeClass
  public static void setUpClass() {
    entityManagerFactory =
        Persistence.createEntityManagerFactory(
                "mytest_" +
                        System.getProperty("profile", "datanucleus").toLowerCase());
  }

  @AfterClass
  public static void tearDownClass() {
    entityManagerFactory.close();
  }

  @Before
  public void setUp() {
    NucleusLogger.GENERAL.info(">> test " + testNameRule.getMethodName() + " START");
    entityManager = entityManagerFactory.createEntityManager();
  }

  @After
  public void tearDown() {
    NucleusLogger.GENERAL.info("<< test " + testNameRule.getMethodName() + " END");
    if (entityManager.getTransaction().isActive()) {
      entityManager.getTransaction().rollback();
    }

    //Wipe everything out
    executeInTransaction(() -> {
      entityManager.getMetamodel().getEntities()
              .stream()
              .map(Type::getJavaType)
              .forEach(type ->
                      entityManager.createQuery(
                                  String.format("DELETE FROM %s t", type.getCanonicalName()))
                              .executeUpdate());
    });
    entityManager.close();
  }

  //No problem with UPDATE queries here
  @Test
  public void testUpdatePerson() {
    final Person person = new Person();
    person.setId(1L);
    person.setEmail("a@b.cd");
    person.setFirstName("Luke");
    person.setLastName("Skywalker");
    executeInTransaction(() -> entityManager.persist(person));

    Person personFromDB = entityManager.find(Person.class, person.getId());
    Assert.assertEquals("a@b.cd", personFromDB.getEmail());
    Assert.assertEquals("Luke", personFromDB.getFirstName());
    Assert.assertEquals("Skywalker", personFromDB.getLastName());

    //Now try to execute an UPDATE query
    executeInTransaction(() -> entityManager
        .createQuery("UPDATE Person p SET p.email = :email WHERE p.id = :id")
        .setParameter("email", "luke@skywalk.er")
        .setParameter("id", person.getId())
        .executeUpdate());

    personFromDB = entityManager.find(Person.class, person.getId());
    Assert.assertEquals("luke@skywalk.er", personFromDB.getEmail());
    Assert.assertEquals("Luke", personFromDB.getFirstName());
    Assert.assertEquals("Skywalker", personFromDB.getLastName());
  }

  //This test does not work with DataNucleus, but does with other JPA providers
  @Test
  public void testUpdateSetting_doesNotWorkWithDN() {
    final Setting setting = new Setting();
    setting.setId(1L);
    setting.setSettingKey("myKey");
    setting.setSettingValue("myValue");
    executeInTransaction(() -> entityManager.persist(setting));

    Setting settingFromDB = entityManager.find(Setting.class, setting.getId());
    Assert.assertEquals("myKey", settingFromDB.getSettingKey());
    Assert.assertEquals("myValue", settingFromDB.getSettingValue());

    //Now try to execute an UPDATE query
    executeInTransaction(() -> entityManager
        .createQuery("UPDATE Setting s SET s.settingValue = :valueToSet WHERE s.id = :id")
        .setParameter("valueToSet", "my.new.value")
        .setParameter("id", setting.getId())
        .executeUpdate());

    settingFromDB = entityManager.find(Setting.class, setting.getId());
    Assert.assertEquals("myKey", settingFromDB.getSettingKey());
    Assert.assertEquals("my.new.value", settingFromDB.getSettingValue());
  }

  private void executeInTransaction(final Runnable operation) {
    EntityTransaction tx = entityManager.getTransaction();
    try {
      tx.begin();
      operation.run();
      entityManager.flush();
      tx.commit();
      entityManager.clear();
    } catch (Throwable thr) {
      final String testMethodName = testNameRule.getMethodName();
      NucleusLogger.GENERAL.error(">> Exception in test " + testMethodName, thr);
      fail("Failed test " + testMethodName + " : " + thr.getMessage());
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
    }
  }
}
