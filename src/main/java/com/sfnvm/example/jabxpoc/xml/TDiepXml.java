package com.sfnvm.example.jabxpoc.xml;

import com.sfnvm.example.jabxpoc.xml.handler.DLieuElementHandler;
import com.sfnvm.example.jabxpoc.xml.validator.TDiepConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TDiep")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"ttchung", "dlieu", "signature"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TDiepConstraint
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TDiepXml {
    @Valid
    @XmlPath("TTChung")
    private TTChungXml ttchung;

    @XmlAnyElement(value = DLieuElementHandler.class)
    private String dlieu;

    @XmlPath("Signature/text()")
    private String signature;
}
