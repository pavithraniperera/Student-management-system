package lk.student.SMS.Service;

import lk.student.SMS.Dto.FeeSchemeDto;

import java.util.List;

public interface FeeSchemeService {
    FeeSchemeDto createFeeScheme(FeeSchemeDto dto);
    FeeSchemeDto getFeeSchemeById(Long id);
    List<FeeSchemeDto> getAll();
    FeeSchemeDto updateFeeScheme(Long id, FeeSchemeDto dto);
    void deleteFeeScheme(Long id);
}
