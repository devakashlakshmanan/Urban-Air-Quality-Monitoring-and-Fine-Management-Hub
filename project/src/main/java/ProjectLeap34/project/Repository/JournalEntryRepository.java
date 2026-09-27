package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
}
