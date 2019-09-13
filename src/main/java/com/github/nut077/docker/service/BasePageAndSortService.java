package com.github.nut077.docker.service;

import com.github.nut077.docker.dto.mapper.BaseMapper;
import com.github.nut077.docker.exception.NotFoundException;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class BasePageAndSortService<E, D, I> {

  private PagingAndSortingRepository<E, I> repository;
  private BaseMapper<E, D> mapper;

  protected BasePageAndSortService(PagingAndSortingRepository<E, I> repository, BaseMapper<E, D> mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<D> findAll() {
    return mapper.mapToListDto(Lists.newArrayList(repository.findAll()));
  }

  public D findById(I id) {
    Optional<E> entity = repository.findById(id);
    return entity.map(mapper::mapToDto).orElseThrow(() -> new NotFoundException("id: " + id + " -->> Not Found"));
  }

  public D create(D dto) {
    return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
  }

  public D update(I id, D dto) {
    findById(id);
    return create(dto);
  }

  public void delete(I id) {
    findById(id);
    repository.deleteById(id);
    log.info("id:{} -->> is deleted", id);
  }
}
