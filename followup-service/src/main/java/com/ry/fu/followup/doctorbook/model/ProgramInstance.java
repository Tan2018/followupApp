package com.ry.fu.followup.doctorbook.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * 流程项目实例表Model
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_FOLLOW_PROGRAM_INSTANCE")
public class ProgramInstance {

    @Id
    private Long id;
    private String STATUS;
    private Long programId;
    private Long flowId;
    private Long reportId;
    private Long recordId;



}
