/*
 * RequestController.java		14/10/2015
 *
 * Copyright (C) 2015 Ackta. All Rights Reserved.
 */
package br.com.ackta.clinical.business;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ackta.clinical.model.entity.Gender;
import br.com.ackta.clinical.model.entity.Patient;
import br.com.ackta.clinical.model.entity.PersonalData;
import br.com.ackta.clinical.model.repository.PatientRepository;
import br.com.ackta.clinical.model.repository.PersonalDataRepository;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
@Controller
@RequestMapping(value = "/")
public class MainController {
	private static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	// private static final int FIRST_INDEX = 0;
	private PatientRepository patientRepository;
	private PersonalDataRepository personalDataRepository;

	@Autowired
	public MainController(PatientRepository patientRepository1, PersonalDataRepository personalDataRepository1) {
		super();
		this.patientRepository = patientRepository1;
		this.personalDataRepository = personalDataRepository1;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getIndex(final Model model) {
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String searchPatient(Form form, final Model model, Pageable pageable) {
		LOGGER.info("Method buscaPorForm initialized.");

		PersonalData data = new PersonalData();
		data.setBirthDate(LocalDate.now());
		data.setCpf("332987002-83");
		data.setGender(Gender.MALE);
		data.setName("name1");
		data.setRg("rg1");
		Patient patient = new Patient(data);
		personalDataRepository.save(data);
		patientRepository.save(patient);
		// String emitente = form.getEmitente();
		// Boolean boleto = form.getBoleto();
		// Boolean processado = form.getProcessado();
		// if (!form.getNfe().isEmpty()) {
		// notaByNfe(form.getNfe(), model);
		// } else {
		// if (!form.getCnpj().isEmpty()) {
		// notaByCnpj(form.getCnpj(), boleto, processado, model, pageable);
		// } else if (!emitente.isEmpty()) {
		// notaByEmintente(emitente, boleto, processado, model, pageable);
		// } else {
		// notaByDefault(boleto, processado, model, pageable);
		// }
		// }
		return "index";
	}
	//
	// @RequestMapping(value = "notaByDefault", method = RequestMethod.GET)
	// public String notaByDefault(
	// Boolean boleto,
	// Boolean processado,
	// final Model model,
	// Pageable pageable) {
	// LOGGER.info("Method notaByDefault initialized.");
	// Page<NotaFiscal> content;
	// NotaFiscal probe = new NotaFiscal();
	// probe.setBoleto(boleto);
	// probe.setProcessado(processado);
	// Example<NotaFiscal> example = Example.of(probe);
	// content = nfeRepository.findAll(example, pageable);
	// atribuiParamModel("", boleto, processado, model, content,
	// "notaByDefault");
	// return "index";
	// }
	//
	// @RequestMapping(value = "notaByEmitente/{emitente}", method =
	// RequestMethod.GET)
	// public String notaByEmintente(
	// @PathVariable String emitente,
	// Boolean boleto,
	// Boolean processado,
	// final Model model,
	// Pageable pageable) {
	// LOGGER.info("Method notaByEmitente initialized.");
	// Page<NotaFiscal> content;
	// NotaFiscal probe = new NotaFiscal();
	// probe.setEmitente(emitente);
	// probe.setBoleto(boleto);
	// probe.setProcessado(processado);
	// ExampleMatcher matcher = ExampleMatcher.matching()
	// .withStringMatcher(StringMatcher.CONTAINING)
	// .withIgnoreCase();
	// Example<NotaFiscal> example = Example.of(probe, matcher);
	// content = nfeRepository.findAll(example, pageable);
	// atribuiParamModel(emitente, boleto, processado, model, content,
	// "notaByEmitente");
	// return "index";
	// }
	//
	// private void atribuiParamModel(String path, Boolean boleto, Boolean
	// processado, final Model model,
	// Page<NotaFiscal> content, String str) {
	// StringBuffer sb = new StringBuffer(str);
	// if (!path.isEmpty()) {
	// sb.append("/");
	// sb.append(path);
	// }
	// String nextSeparador = "?";
	// if (Objects.nonNull(boleto)) {
	// sb.append(nextSeparador);
	// nextSeparador = "&";
	// sb.append("boleto=");
	// sb.append(boleto);
	// }
	// if (Objects.nonNull(processado)) {
	// sb.append(nextSeparador);
	// nextSeparador = "&";
	// sb.append("processado=");
	// sb.append(processado);
	// }
	// model.addAttribute("path", sb.toString());
	// model.addAttribute("nfList", content);
	// model.addAttribute("page", "nf/nfList");
	// for(NotaFiscal nf : content.getContent()) {
	// if (Objects.isNull(nf.getEstadoAtual())) {
	// saveEstadoNf(nf, Estado.SEM_BOLETO);
	// }
	// }
	// }
	//
	// @RequestMapping(value = "notaByCnpj/{cnpj}", method = RequestMethod.GET)
	// public String notaByCnpj(
	// @PathVariable String cnpj,
	// Boolean boleto,
	// Boolean processado,
	// Model model,
	// Pageable pageable) {
	// LOGGER.info("Method notaByCnpj initialized.");
	// Page<NotaFiscal> content;
	// NotaFiscal probe = new NotaFiscal();
	// probe.setCnpj(cnpj);
	// probe.setBoleto(boleto);
	// probe.setProcessado(processado);
	// Example<NotaFiscal> example = Example.of(probe);
	// content = nfeRepository.findAll(example, pageable);
	// atribuiParamModel(cnpj, boleto, processado, model, content,
	// "notaByCnpj");
	// return "index";
	// }
	//
	// /**
	// * @param form
	// * @return
	// */
	// private String notaByNfe(String nfe, final Model model) {
	// List<NotaFiscal> list =
	// Lists.newArrayList(nfeRepository.findByNfe(nfe).get());
	// Page<NotaFiscal> content = new PageImpl<>(list);
	// atribuiParamModel(nfe, false, false, model, content, "notaByNfe");
	// return "index";
	// }
	//
	// @RequestMapping(value = "marcarBoleto/{nfe}", method =
	// RequestMethod.POST)
	// public String marcarBoleto(String nfe, MalaDiretaForm form, final Model
	// model) {
	// LOGGER.info("Method marcarBoleto initialized.");
	// Optional<NotaFiscal> optNf = nfeRepository.findByNfe(nfe);
	// if (optNf.isPresent()) {
	// NotaFiscal nf = optNf.get();
	// DadosMalaDireta dados = nf.getDadosMalaDireta();
	// dados.setEmailDestino(form.getEmailDestino());
	// dados.setNumProcesso(form.getNumProcesso());
	// dados.setPesquisador(form.getPesquisador());
	// dados.setVencimento(form.getVencimento());
	// dadosMalaDiretaRep.save(dados);
	// saveEstadoNf(nf, Estado.COM_BOLETO);
	// }
	// model.addAttribute("record", new Form());
	// model.addAttribute("page", "nf/searchNf");
	// return "index";
	// }
	//
	// /**
	// * @param nf
	// */
	// private void saveEstadoNf(NotaFiscal nf, Estado estado) {
	// EstadoNotaFiscal novoEstado = new EstadoNotaFiscal(nf, estado);
	// estadoNfRep.save(novoEstado);
	// nf.setEstadoAtual(novoEstado);
	// nfeRepository.save(nf);
	// }
	//
	// @RequestMapping(value = "marcarNfe", method = RequestMethod.POST)
	// public String marcaNfe(NfListForm form, final Model model) {
	// LOGGER.info("Method marcaNfe initialized.");
	// for (NotaFiscal nf : form.getContent()) {
	// save(nf);
	// }
	// model.addAttribute("record", new Form());
	// model.addAttribute("page", "nf/searchNf");
	// return "index";
	// }
	//
	// private void save(NotaFiscal nf) {
	// NotaFiscal nfe = nfeRepository.findByNfe(nf.getNfe()).get();
	// nfe.setBoleto(nf.getBoleto());
	// nfe.setProcessado(nf.getProcessado());
	// nfe.setDadosMalaDireta(nf.getDadosMalaDireta());
	// boolean nfeProcessado = Objects.nonNull(nfe.getDataProc());
	// if (!nf.getProcessado().equals(nfeProcessado)) {
	// if (nf.getProcessado()) {
	// nfe.setDataProc(LocalDateTime.now());
	// } else {
	// nfe.setDataProc(null);
	// }
	// }
	// nfeRepository.save(nfe);
	// }
	//
	// @RequestMapping(value = "detalhes/{nfe}", method = RequestMethod.GET)
	// public String buscaDetalhesNfe(@PathVariable String nfe, final Model
	// model) {
	// LOGGER.info("Method buscaDetalhesNfe initialized.");
	// NotaFiscal nf = nfeRepository.findByNfe(nfe).get();
	// List<Long> numProcs = ProcessUtil.extractFrom(nf.getObservacao());
	// List<ProwbProcesso> listProcs = Lists.newArrayList();
	// if (!numProcs.isEmpty()) {
	// listProcs = procRepository.findByNumProwbIn(numProcs);
	// if (!listProcs.isEmpty()) {
	// nf.setProcessos(listProcs);
	// } else {
	// List<ProwbProcesso> newProcs = numProcs.stream()
	// .map(n -> new ProwbProcesso(n))
	// .collect(Collectors.toList());
	// nf.setProcessos(newProcs);
	// }
	// }
	// if (Objects.isNull(nf.getDadosMalaDireta()) ) {
	// criandoDadosMalaDireta(nf, numProcs, listProcs);
	// }
	// model.addAttribute("nfDetail", nf);
	// model.addAttribute("page", "nf/details");
	// return "index";
	// }
	//
	// @RequestMapping(value = "procuraProcesso/{numProcesso}", method =
	// RequestMethod.GET)
	// public String procuraProcesso(@PathVariable Long numProcesso, final Model
	// model) {
	// LOGGER.info("Method procuraProcesso initialized.");
	// Optional<ProwbProcesso> processo =
	// procRepository.findByNumProwb(numProcesso);
	// if (processo.isPresent()) {
	// PeswbPessoa pesquisador = procuraPesquisador(processo.get());
	// if (Objects.nonNull(pesquisador)) {
	// model.addAttribute("nfDetail.dadosMalaDireta.pesquisador",
	// pesquisador.getNomePeswb());
	// model.addAttribute("nfDetail.dadosMalaDireta.pesquisador",
	// pesquisador.getNomePeswb());
	// }
	// }
	// return "index";
	// }
	//
	// private void criandoDadosMalaDireta(NotaFiscal nf, List<Long> numProcs,
	// List<ProwbProcesso> listProcs) {
	// DadosMalaDireta dadosMalaDireta = new DadosMalaDireta(nf);
	// if (!numProcs.isEmpty()) {
	// if (numProcs.size() == 1) {
	// dadosMalaDireta.setNumProcesso(numProcs.get(FIRST_INDEX));
	// if (listProcs.size() == 1) {
	// PeswbPessoa pesquisador = procuraPesquisador(listProcs.get(FIRST_INDEX));
	// if (Objects.nonNull(pesquisador))
	// dadosMalaDireta.setPesquisador(pesquisador.getNomePeswb());
	// dadosMalaDireta.setEmailDestino(pesquisador.getEmaPeswb());
	// }
	// }
	// }
	// nf.setDadosMalaDireta(dadosMalaDireta);
	// dadosMalaDiretaRep.save(dadosMalaDireta);
	// save(nf);
	// }
	//
	// private PeswbPessoa procuraPesquisador(ProwbProcesso processo) {
	// PeswbPessoa solicitante = processo.getPeswbCodSolPeswb();
	// PeswbPessoa orientador = processo.getPeswbCodOriPeswb();
	// PeswbPessoa result;
	// if (Objects.nonNull(solicitante)) {
	// result = solicitante;
	// } else {
	// result = orientador;
	// }
	// return result;
	// }
}
