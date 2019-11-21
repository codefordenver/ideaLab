package idealab.api.dto.response;

import java.util.List;
import java.util.Objects;

public class BasicEmployeeResponse extends GenericResponse {

    private List<BasicEmployee> basicEmployeeList;

    public BasicEmployeeResponse() {
    }

    public BasicEmployeeResponse(List<BasicEmployee> basicEmployeeList) {
        this.basicEmployeeList = basicEmployeeList;
    }

    public List<BasicEmployee> getBasicEmployeeList() {
        return basicEmployeeList;
    }

    public void setBasicEmployeeList(List<BasicEmployee> basicEmployeeList) {
        this.basicEmployeeList = basicEmployeeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasicEmployeeResponse that = (BasicEmployeeResponse) o;
        return Objects.equals(basicEmployeeList, that.basicEmployeeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basicEmployeeList);
    }

    @Override
    public String toString() {
        return "BasicEmployeeResponse{" +
                "basicEmployeeList=" + basicEmployeeList +
                '}';
    }
}