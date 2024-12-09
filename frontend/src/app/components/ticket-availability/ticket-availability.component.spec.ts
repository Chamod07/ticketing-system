import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketAvailabilityComponent } from './ticket-availability.component';

describe('TicketAvailabilityComponent', () => {
  let component: TicketAvailabilityComponent;
  let fixture: ComponentFixture<TicketAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketAvailabilityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
