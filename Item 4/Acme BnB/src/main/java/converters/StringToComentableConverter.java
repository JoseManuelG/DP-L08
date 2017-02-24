package converters;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ComentableRepository;
import domain.Comentable;

@Component
@Transactional
public class StringToComentableConverter implements Converter<String, Comentable> {

	@Autowired
	ComentableRepository comentableRepository;

	@Override
	public Comentable convert(String text) {
		Comentable result;
		int id;

		try {
			id = Integer.valueOf(text);
			Collection<Comentable> recipient2 =  comentableRepository.findAll();

			result = comentableRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
