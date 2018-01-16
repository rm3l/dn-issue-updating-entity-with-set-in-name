package mydomain.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
public class Person {

  @Id
  private Long id;

  @Basic(optional = false)
  @Column(unique = true)
  private String email;

  @Basic(optional = false)
  private String firstName;

  @Basic
  private String lastName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(getEmail(), person.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmail());
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
