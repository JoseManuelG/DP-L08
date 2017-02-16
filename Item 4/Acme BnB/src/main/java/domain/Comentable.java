
package domain;

import java.util.Collection;

public interface Comentable {

	Collection<Comment> getPostedComments();
	void setPostedComments(Collection<Comment> postedComments);
}
