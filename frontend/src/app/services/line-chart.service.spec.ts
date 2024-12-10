import { TestBed } from '@angular/core/testing';

import { LineChartService } from './line-chart.service';

describe('LineChartService', () => {
  let service: LineChartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LineChartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
