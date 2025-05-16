package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.CourseRepository;
import lk.student.SMS.Dao.FeeSchemeRepository;
import lk.student.SMS.Dao.PaymentPlanRepository;
import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Dto.PaymentPlanDto;
import lk.student.SMS.Entity.Course;
import lk.student.SMS.Entity.FeeScheme;
import lk.student.SMS.Entity.PaymentPlan;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Exception.ResourceNotFoundException;
import lk.student.SMS.Service.FeeSchemeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeeSchemeServiceImpl implements FeeSchemeService {

    private final FeeSchemeRepository feeSchemeRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final PaymentPlanRepository paymentPlanRepository;

    @Autowired
    public FeeSchemeServiceImpl(FeeSchemeRepository feeSchemeRepository,
                                UserRepository userRepository,
                                CourseRepository courseRepository,
                                ModelMapper modelMapper,
                                PaymentPlanRepository paymentPlanRepository) {
        this.feeSchemeRepository = feeSchemeRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.paymentPlanRepository = paymentPlanRepository;
    }

    @Override
    public FeeSchemeDto createFeeScheme(FeeSchemeDto dto) {
        if (dto.getCreatedBy() == null) {
            throw new ResourceNotFoundException("CreatedBy user ID must not be null");
        }

        if (dto.getCourseId() == null) {
            throw new ResourceNotFoundException("Course ID must not be null");
        }

        User user = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getCreatedBy()));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + dto.getCourseId()));

        FeeScheme feeScheme = new FeeScheme();
        feeScheme.setSchemeName(dto.getSchemeName());
        feeScheme.setCreatedDate(LocalDate.now());
        feeScheme.setCurrency(dto.getCurrency());
        feeScheme.setCreatedBy(user);
        feeScheme.setCourse(course);

        List<PaymentPlan> plans = new ArrayList<>();

        for (PaymentPlanDto p : dto.getPaymentPlans()) {
            Optional<PaymentPlan> existingPlanOpt = paymentPlanRepository
                    .findByFeeTypeAndAmountAndDescription(p.getFeeType(), p.getAmount(), p.getDescription());

            PaymentPlan plan = existingPlanOpt.orElseGet(() -> {
                PaymentPlan newPlan = new PaymentPlan();
                newPlan.setFeeType(p.getFeeType());
                newPlan.setDescription(p.getDescription());
                newPlan.setAmount(p.getAmount());
                return newPlan;
            });

            plan.setFeeScheme(feeScheme);
            plans.add(plan);
        }

        feeScheme.setPaymentPlans(plans);

        FeeScheme saved = feeSchemeRepository.save(feeScheme);

        modelMapper.typeMap(FeeScheme.class, FeeSchemeDto.class).addMappings(mapper ->
                mapper.map(src -> src.getCreatedBy().getId(), FeeSchemeDto::setCreatedBy)
        );

        return modelMapper.map(saved, FeeSchemeDto.class);
    }

    @Override
    public FeeSchemeDto getFeeSchemeById(Long id) {
        FeeScheme feeScheme = feeSchemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FeeScheme not found with ID: " + id));

        return modelMapper.map(feeScheme, FeeSchemeDto.class);
    }

    @Override
    public List<FeeSchemeDto> getAll() {
        modelMapper.typeMap(FeeScheme.class, FeeSchemeDto.class).addMappings(mapper ->
                mapper.map(src -> src.getCreatedBy().getId(), FeeSchemeDto::setCreatedBy)
        );

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
                .orElseThrow(() -> new ResourceNotFoundException("FeeScheme not found with ID: " + id));

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
        if (!feeSchemeRepository.existsById(id)) {
            throw new ResourceNotFoundException("FeeScheme not found with ID: " + id);
        }

        feeSchemeRepository.deleteById(id);
    }
}
