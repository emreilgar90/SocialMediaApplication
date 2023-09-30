package com.emreilgar.utillity;

import java.util.List;
import java.util.Optional;

/**
 * Service sözleşmesi,burada kalıp olarak oluşturulan methodlar
 * tüm serviceler de aynı şekilde kullanılmak zorundadır.
 * SORUNLAR;
 * 1-Eğer burada ki mothodlar tüm serviceler tarafından kullanılacak ise,
 * kaydetme işleminde hangi entity adı yazılmalı ?
 * ÇÖZÜM: Type olarak belirsiz durumlarda, Object gibi katı türler kullanmak yerine esnek bir şablon çıkarmak
 * doğru olacaktır.
 * 2-Peki, id her zaman long olamaz, String olabilir,Integer olabilir  o zaman id için
 * Long kullanmak zorunluluk yaratacaktır.**/
public interface IService<T,ID> {
    T save(T t);
    Iterable<T> saveAll(Iterable<T> T);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    List<T> findAll();
    Optional <T> findById(ID id);
}
