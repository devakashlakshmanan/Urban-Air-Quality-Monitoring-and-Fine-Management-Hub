package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryResponseDTO {

    private Long id;
    private String ReferenceNumber;
    private String EntryDate;
    private String AccountCode;
    private String Description;
    private Double Debit;
    private Double Credit;
    private String ReferenceType;
    private Long ReferenceId;
}
