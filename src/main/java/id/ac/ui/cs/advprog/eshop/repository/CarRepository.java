package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarRepository extends CrudRepository<Car, String> {
    @Override
    Car create(Car car);

    @Override
    java.util.Iterator<Car> findAll();

    @Override
    Car findById(String carId);

    @Override
    Car update(Car car);

    @Override
    void delete(String carId);
}
