package com.kavak.main_core.controllers;

import com.kavak.main_core.dto.MainDTO;
import com.kavak.main_core.entity.MainEntity;
import com.kavak.main_core.mapper.IMainMapper;
import com.kavak.main_core.service.IMainService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public abstract class MainController <Entity extends MainEntity, DTO extends MainDTO, Key extends Object>
	implements IMainService<Entity, DTO, Key> {

	private final IMainService<Entity, DTO, Key> mainService;

	protected final PagingAndSortingRepository<Entity, Key> repository;

	protected final IMainMapper<Entity, DTO> mapper;

	public MainController(IMainService<Entity, DTO, Key> service) {
		this.mainService = service;
		this.repository = service.getRepository();
		this.mapper = service.getMapper();
	}

	public IMainService<Entity, DTO, Key> getMainService() {
		return mainService;
	}

	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<DTO> create(@Valid @RequestBody DTO dto, HttpServletRequest request)
			throws URISyntaxException {
		if (dto.getId() != null && (!dto.getId().toString().isEmpty())) {
			return ResponseEntity.badRequest().header("Failure", "Una nueva entidad no puede tener un ID").body(null);
		}

		try {
			DTO createdEntity = create(dto, mapper);
			return ResponseEntity.created(new URI(createdEntity.getId().toString())).body(createdEntity);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<DTO> update(@PathVariable Key id, @Valid @RequestBody DTO dto, HttpServletRequest request)
			throws URISyntaxException {
		try {
			DTO updateDTO = update(id, dto, mapper);
			return null != updateDTO ? new ResponseEntity<>(updateDTO, HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> get(@PathVariable Key id, HttpServletRequest request) {
		try {
			DTO obj = get(id, mapper);
			return null != obj ? new ResponseEntity<>(obj, HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> getCount(HttpServletRequest request) {
		try {
			long obj = getCount();
			return obj>0 ? new ResponseEntity<>(obj, HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<DTO> delete(@PathVariable Key id, HttpServletRequest request) {
		try {
			delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DTO>> getAll(Pageable pageable, HttpServletRequest request) throws URISyntaxException {
		try {
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageable = PageRequest.of(pageNumber, pageSize);
		} catch (Exception ex) {
		}

		try {
			Page<DTO> page = getAll(pageable, mapper);
			HttpHeaders headers = generatePaginationHttpHeaders(page, "");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		} catch (Throwable ex) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
		String link = "";
		if ((page.getNumber() + 1) < page.getTotalPages()) {
			link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
		}
		// prev link
		if ((page.getNumber()) > 0) {
			link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
		}
		// last and first link
		int lastPage = 0;
		if (page.getTotalPages() > 0) {
			lastPage = page.getTotalPages() - 1;
		}
		link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
		link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
		headers.add(HttpHeaders.LINK, link);
		return headers;
	}

	private static String generateUri(String baseUrl, int page, int size) {
		return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size)
				.toUriString();
	}

	@Override
	public PagingAndSortingRepository<Entity, Key> getRepository() {
		return repository;
	}

	@Override
	public IMainMapper<Entity, DTO> getMapper() {
		return mapper;
	}

	@Override
	public DTO create(DTO dto, IMainMapper<Entity, DTO> mapper) {
		return mainService.create(dto, mapper);
	}

	@Override
	public DTO update(Key id, DTO dto, IMainMapper<Entity, DTO> mapper) {
		return mainService.update(id, dto, mapper);
	}

	@Override
	public DTO get(Key id, IMainMapper<Entity, DTO> mapper) {
		return mainService.get(id, mapper);
	}

	@Override
	public Page<DTO> getAll(Pageable pageable, IMainMapper<Entity, DTO> mapper) {
		return mainService.getAll(pageable, mapper);
	}

	@Override
	public void delete(Key id) {
		mainService.delete(id);
	}

	@Override
	public long getCount(){
		return mainService.getCount();
	}
}
