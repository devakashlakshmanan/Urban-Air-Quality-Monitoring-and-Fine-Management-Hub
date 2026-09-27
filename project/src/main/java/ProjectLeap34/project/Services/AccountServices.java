package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.AccountRequestDTO;
import ProjectLeap34.project.DTO.AccountResponseDTO;
import ProjectLeap34.project.Models.Account;
import ProjectLeap34.project.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServices {

    @Autowired
    private AccountRepository accountRepository;

    private Account convertToEntity(AccountRequestDTO dto) {
        Account account = new Account();
        account.setAccountCode(dto.getAccountCode());
        account.setAccountName(dto.getAccountName());
        account.setAccountType(dto.getAccountType());
        account.setDescription(dto.getDescription());
        account.setBalance(dto.getBalance());
        account.setActive(dto.getActive());
        return account;
    }

    private AccountResponseDTO convertToResponseDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setAccountCode(account.getAccountCode());
        dto.setAccountName(account.getAccountName());
        dto.setAccountType(account.getAccountType());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setActive(account.getActive());
        return dto;
    }

    public AccountResponseDTO createaccount(AccountRequestDTO data) {
        Account account = convertToEntity(data);
        Account saved = accountRepository.save(account);
        return convertToResponseDTO(saved);
    }

    public List<AccountResponseDTO> getallaccount() {
        return accountRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AccountResponseDTO updateaccount(AccountRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Account ID is required for update");
        }
        Account account = accountRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
        if (data.getAccountCode() != null) account.setAccountCode(data.getAccountCode());
        if (data.getAccountName() != null) account.setAccountName(data.getAccountName());
        if (data.getAccountType() != null) account.setAccountType(data.getAccountType());
        if (data.getDescription() != null) account.setDescription(data.getDescription());
        if (data.getBalance() != null) account.setBalance(data.getBalance());
        if (data.getActive() != null) account.setActive(data.getActive());
        Account saved = accountRepository.save(account);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        accountRepository.deleteById(Id);
    }

    public AccountResponseDTO getbyid(Long Id) {
        Account account = accountRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
        return convertToResponseDTO(account);
    }
}
