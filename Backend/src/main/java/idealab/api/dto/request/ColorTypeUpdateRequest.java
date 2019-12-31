package idealab.api.dto.request;

import idealab.api.exception.IdeaLabApiException;

import java.util.Objects;

import static idealab.api.exception.ErrorType.VALIDATION_ERROR;

/**
 * This class is used to update the color type availability.
 */
public class ColorTypeUpdateRequest implements GenericRequest {

    private Boolean availability;

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    //Currently needed for test classes
    // I think it's better to achieve that without using this. It's bad practice to have a code which is only used for testing purposes ~ kaansonmezoz
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorTypeUpdateRequest that = (ColorTypeUpdateRequest) o;
        return Objects.equals(availability, that.availability);
    }

    @Override
    public String toString() {
        return "PrintJobUpdateRequest{" +
                ", availability='" + availability + '\'' +
                '}';
    }

    @Override
    public void validate() {
        if(this.availability == null)
            throw new IdeaLabApiException(VALIDATION_ERROR, "Availability cannot be empty");
    }
}
