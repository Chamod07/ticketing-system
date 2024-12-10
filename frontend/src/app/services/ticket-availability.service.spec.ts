import { TestBed } from '@angular/core/testing';

import { TicketAvailabilityService } from './ticket-availability.service';

describe('TicketAvailabilityService', () => {
  let service: TicketAvailabilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketAvailabilityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
