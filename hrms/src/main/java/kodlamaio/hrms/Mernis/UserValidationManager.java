package kodlamaio.hrms.Mernis;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class UserValidationManager implements UserValidationService{

	@Override
	public boolean validate(Candidate candidate) {
		return true;
	}
}
