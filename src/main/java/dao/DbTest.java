package main.java.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.java.injector.MongoModule;
import main.java.model.db.BtcUsdT;

import java.util.List;

public class DbTest {
  public static void main(String[] args) {
    Injector mongoDaoInjector = Guice.createInjector(new MongoModule());
    BtcUsdDao dao = mongoDaoInjector.getInstance(BtcUsdDao.class);
    List<BtcUsdT> list = dao.getAll();
    System.out.println(list.size());

  }
}
