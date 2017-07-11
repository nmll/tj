package com.f.tj.repository;

import com.f.tj.entity.Goods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by fei on 2017/7/10.
 */
public interface GoodsRepository extends CrudRepository<Goods,String> {


    @Query(value = "select g from Goods g  where name <> tbName or name is null")
    public Iterable<Goods> findGoods();

    @Modifying
    @Transactional
    @Query(value = "update Goods g set g.name = g.tbName where g.name <> g.tbName or g.name is null")
    public int updateName();
}
