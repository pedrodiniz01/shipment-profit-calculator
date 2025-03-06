import { Directive, Input } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  // Allows method being called in html
  selector: '[positiveAmount],[notEmpty]',
  providers: [
    {
      provide: NG_VALIDATORS, // angular will handle as validator
      useExisting: ValidatorService,
      multi: true
    }
  ]
})
export class ValidatorService implements Validator {
  // Attribute, starts as false
  @Input() positiveAmount: boolean = false;
  @Input() notEmpty: boolean = false;

  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    const errors: ValidationErrors = {};

    
    if (this.notEmpty) {
      if (value === null || value === undefined || (typeof value === 'string' && value.trim() === '')) {
        errors['notEmpty'] = true;
      }
    }

   
    if (this.positiveAmount) {
      if (value !== null && value !== undefined && value !== '') {
        if (typeof value === 'number' && value <= 0) {
          errors['positiveAmount'] = true;
        }
      }
    }

    return Object.keys(errors).length ? errors : null;
  }
}
