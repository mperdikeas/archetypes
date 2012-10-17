package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CAR_INFO_REVIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarInfoReview.findAll"                , query = "SELECT x FROM CarInfoReview x"),
    @NamedQuery(name = "CarInfoReview.findByModelSpecReviewer", query = "SELECT x FROM CarInfoReview x WHERE x.modelSpecReviewer = :MODELSPECREVIEWER")
        }) 
public class CarInfoReview implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Embedded
    @AttributeOverrides({
                    @AttributeOverride(name="model"    , column=@Column(name="MODEL")),
                    @AttributeOverride(name="spec"     , column=@Column(name="SPEC")),
                    @AttributeOverride(name="reviewer" , column=@Column(name="SPEC_REVIEWER"))
                        })
    private ModelSpecReviewer modelSpecReviewer;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SPEC_REVIEWER_COMMENTS")
    private String specReviewerComments;


    public CarInfoReview() {}

    public CarInfoReview(ModelSpecReviewer modelSpecReviewer, String specReviewerComments) {
        this.modelSpecReviewer = modelSpecReviewer;
        this.specReviewerComments = specReviewerComments;
    }

    public ModelSpecReviewer getModelSpecReviewer() {
        return modelSpecReviewer;
    }

    public void setModelSpecReviewer(ModelSpecReviewer modelSpecReviewer) {
        this.modelSpecReviewer = modelSpecReviewer;
    }

    public String getSpecReviewerComments() {
        return specReviewerComments;
    }

    public void setSpecReviewerComments(String specReviewerComments) {
        this.specReviewerComments = specReviewerComments;
    }
    
    public int getHashCode() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash +=     (modelSpecReviewer          != null ? modelSpecReviewer.hashCode()    : 0);
        hash += 173*(specReviewerComments       != null ? specReviewerComments.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarInfoReview)) {
            return false;
        }
        CarInfoReview other = (CarInfoReview) object;
        if ((this.modelSpecReviewer == null && other.modelSpecReviewer != null) || 
            (this.modelSpecReviewer != null && !this.modelSpecReviewer.equals(other.modelSpecReviewer))) {
            return false;
        }
        if ((this.specReviewerComments == null && other.specReviewerComments != null) || 
            (this.specReviewerComments != null && !this.specReviewerComments.equals(other.specReviewerComments))) {
            return false;
        } 
        return true;
    }

    @Override
    public String toString() {
        return CarInfo.class.getName()+"[ modelSpecReviewer =" + modelSpecReviewer+", specReviewerComments = "+specReviewerComments+" ]";
    }
}
