import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';


@Directive({
  selector: '[appPositiveAmount]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: PositiveAmountValidatorDirective,
      multi: true
    }
  ]
})
export class PositiveAmountValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    // If empty, let the required validator handle it.
    if (value === null || value === undefined || value === '') {
      return null;
    }
    // If the value is less than or equal to zero, return an error.
    return value > 0 ? null : { positiveAmount: true };
  }
}
