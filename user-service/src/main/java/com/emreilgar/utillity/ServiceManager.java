package com.emreilgar.utillity;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/** <T> Bu sınıfı miras almak isteyen bir service hangi entity'e hizmet ediyorsa onun type
 * olarak verecek
 *
 * <ID> Bu sınıfı miras alan servisin kullanmakta olduğu id tipini belirlemesi gerekir.
 * Long,String ,Integer*/
public class ServiceManager<T ,ID> implements IService<T,ID>{

    /**T EXTENDS BASEENTİTY YAPMAYI UNUTMA !!!!!!!!!!!!!!!!!!!!!!!!**/

    /**Tüm serviceler için ortak bir kullanım sunacak ise, burada repository
     * üzerinden işlem yapabiliyor olmalı
     ***/
    private final MongoRepository<T,ID> repository;

    public ServiceManager(MongoRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {

        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
         repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);

    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}
