import { TestBed } from '@angular/core/testing';

import {CountDisplayService} from './count-display.service';

describe('CountDisplayService', () => {
  let service: CountDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CountDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
