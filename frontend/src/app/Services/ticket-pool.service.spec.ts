import { TestBed } from '@angular/core/testing';

import { TicketPoolService } from './ticket-pool.service';

describe('TicketPoolService', () => {
  let service: TicketPoolService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketPoolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
