package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.FeeSchemeRepository;
import lk.student.SMS.Dao.PaymentPlanRepository;
import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Dto.PaymentPlanDto;
import lk.student.SMS.Entity.FeeScheme;
import lk.student.SMS.Entity.PaymentPlan;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Service.FeeSchemeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeeSchemeServiceImpl implements FeeSchemeService {

    private final FeeSchemeRepository feeSchemeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PaymentPlanRepository paymentPlanRepository;

    @Autowired
    public FeeSchemeServiceImpl(FeeSchemeRepository feeSchemeRepository, UserRepository userRepository, ModelMapper modelMapper, PaymentPlanRepository paymentPlanRepository) {
        this.feeSchemeRepository = feeSchemeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.paymentPlanRepository = paymentPlanRepository;
    }

    @Override
    public FeeSchemeDto createFeeScheme(FeeSchemeDto dto) {
        FeeScheme feeScheme = new FeeScheme();
        feeScheme.setSchemeName(dto.getSchemeName());
        feeScheme.setCreatedDate(LocalDate.now());
        feeScheme.setCurrency(dto.getCurrency());
        if (dto.getCreatedByUserId() == null) {
            throw new IllegalArgumentException("CreatedBy user ID must not be null");
        }else {
            User user = userRepository.findById(dto.getCreatedByUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            feeScheme.setCreatedBy(user);

        }


        List<PaymentPlan> plans = new ArrayList<>();

        for (PaymentPlanDto p : dto.getPaymentPlans()) {
            Optional<PaymentPlan> existingPlanOpt = paymentPlanRepository
                    .findByFeeTypeAndAmountAndDescription(p.getFeeType(), p.getAmount(), p.getDescription());

            PaymentPlan plan;

            if (existingPlanOpt.isPresent()) {
                // Reuse existing plan (optional: clone or validate it's OK to reuse)
                plan = existingPlanOpt.get();
            } else {
                // Create new
                plan = new PaymentPlan();
                plan.setFeeType(p.getFeeType());
                plan.setDescription(p.getDescription());
                plan.setAmount(p.getAmount());
            }

            plan.setFeeScheme(feeScheme); // Always associate with the current FeeScheme
            plans.add(plan);
        }

        feeScheme.setPaymentPlans(plans);


        FeeScheme saved = feeSchemeRepository.save(feeScheme);
        return modelMapper.map(saved, FeeSchemeDto.class);
    }

    @Override
    public FeeSchemeDto getFeeSchemeById(Long id) {
        FeeScheme feeScheme = feeSchemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return modelMapper.map(feeScheme, FeeSchemeDto.class);
    }

    @Override
    public List<FeeSchemeDto> getAll() {
        List<FeeScheme> feeSchemes = feeSchemeRepository.findAll();
        List<FeeSchemeDto> dtoList = new ArrayList<>();

        for (FeeScheme fee : feeSchemes) {
            dtoList.add(modelMapper.map(fee, FeeSchemeDto.class));
        }

        return dtoList;

    }

    @Override
    public FeeSchemeDto updateFeeScheme(Long id, FeeSchemeDto dto) {
        FeeScheme feeScheme = feeSchemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        feeScheme.setSchemeName(dto.getSchemeName());
        feeScheme.setCurrency(dto.getCurrency());

        // Clear and replace payment plans
        feeScheme.getPaymentPlans().clear();

        List<PaymentPlan> updatedPlans = new ArrayList<>();
        for (PaymentPlanDto p : dto.getPaymentPlans()) {
            PaymentPlan plan = new PaymentPlan();
            plan.setFeeScheme(feeScheme);
            plan.setFeeType(p.getFeeType());
            plan.setDescription(p.getDescription());
            plan.setAmount(p.getAmount());
            updatedPlans.add(plan);
        }


        feeScheme.getPaymentPlans().addAll(updatedPlans);

        return modelMapper.map(feeSchemeRepository.save(feeScheme), FeeSchemeDto.class);
    }

    @Override
    public void deleteFeeScheme(Long id) {
        feeSchemeRepository.deleteById(id);
    }
}
