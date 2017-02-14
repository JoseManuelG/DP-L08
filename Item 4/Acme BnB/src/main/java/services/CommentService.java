
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.LoginService;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	//Managed Repository-----------------------------

	@Autowired
	private CommentRepository	commentRepository;

	//Supporting services-----------------------------

	@Autowired
	private LoginService		loginService;


	//Constructors------------------------------------

	public CommentService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public Comment create() {
		Comment result;

		result = new Comment();

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = commentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Comment findOne(int commentId) {
		Comment result;

		result = commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public Comment save(Comment comment) {
		Assert.notNull(comment, "El comentario no puede ser nulo");
		Assert.isTrue(comment.getId() == 0, "No se pueden modificar comentarios");
		Assert.hasText(comment.getTitle(), "El titulo no puede ser nulo ni estar vacío");
		Assert.hasText(comment.getText(), "El texto no puede ser nulo ni estar vacío");
		Assert.isTrue(comment.getStars() != 0, "No se puede dejar un comentario sin estrellas");
		Assert.notNull(comment.getPostMoment(), "La fecha de creación no puede ser nula");
		Assert.notNull(comment.getRecipient(), "El Recipient no puede ser nulo");
		Assert.isTrue(comment.getSender().getUserAccount().equals(loginService.getPrincipal()), "Solo el propietario puede realizar operaciones");
		Comment result;

		result = commentRepository.save(comment);

		return result;
	}

	//Other bussiness methods------------------------

}
