package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<SaleMinDTO> getSalesReport(String minDateStr, String maxDateStr, String name) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isEmpty()) ? today : LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isEmpty()) ? maxDate.minusYears(1L) : LocalDate.parse(minDateStr);
		name = (name == null || name.isEmpty()) ? "" : name;
		return repository.findSalesReport(minDate, maxDate, name);
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryDTO> getSalesSummary(String minDateStr, String maxDateStr) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isEmpty()) ? today : LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isEmpty()) ? maxDate.minusYears(1L) : LocalDate.parse(minDateStr);

		return repository.findSalesSummary(minDate, maxDate);
	}

}
