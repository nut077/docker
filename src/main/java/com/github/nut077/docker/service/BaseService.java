package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.mapper.BaseMapper;
import com.github.nut077.docker.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class BaseService<E, D, I> {

  private JpaRepository<E, I> repository;
  private BaseMapper<E, D> mapper;

  protected BaseService(JpaRepository<E, I> repository, BaseMapper<E, D> mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<D> findAll() {
    return mapper.mapToListDto(repository.findAll());
  }

  public D findById(I id) {
    Optional<E> entity = repository.findById(id);
    return entity.map(mapper::mapToDto).orElseThrow(() -> new NotFoundException("id: " + id + " -->> Not Found"));
  }

  public D save(D dto) {
    return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
  }

  public D update(I id, D dto) {
    findById(id);
    return save(dto);
  }

  public void delete(I id) {
    findById(id);
    repository.deleteById(id);
    log.info("id -->> {} is deleted", id);
  }
}
