package cn.hjmao.auth.entity;

import cn.hjmao.auth.config.JwtSettings;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = JwtSettings.TABLE_NAME_ROLE)
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String comment;

  public RoleEntity() { }

  public RoleEntity(String name) {
    this.name = name;
  }
}
