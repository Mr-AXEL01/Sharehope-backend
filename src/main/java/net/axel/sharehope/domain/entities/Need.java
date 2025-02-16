package net.axel.sharehope.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.axel.sharehope.domain.enums.NeedStatus;

@Entity
@Table(name = "needs")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Need extends Action {
    
    @Enumerated(EnumType.STRING)
    private NeedStatus needStatus;
}
