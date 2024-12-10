import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { Subscription } from 'rxjs';
import { LineChartService } from '../../services/line-chart.service';
import { UIChart } from 'primeng/chart';

@Component({
  selector: 'app-line-chart',
  standalone: true,
  imports: [
    ChartModule
  ],
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit, OnDestroy {
  @ViewChild('chart') chart: UIChart | undefined;

  data: any;
  options: any;
  private dataSubscription: Subscription | undefined;

  private labels: string[] = [];
  private purchasedData: number[] = [];
  private releasedData: number[] = [];

  constructor(private lineChartService: LineChartService) {}

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data = {
      labels: this.labels,
      datasets: [
        {
          label: 'Tickets Released',
          data: this.releasedData,
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          tension: 0.4
        },
        {
          label: 'Tickets Purchased',
          data: this.purchasedData,
          fill: false,
          borderColor: documentStyle.getPropertyValue('--red-500'),
          tension: 0.4
        }
      ]
    };

    this.options = {
      responsive: true,
      maintainAspectRatio: false,
      aspectRatio: 0.6,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }
      }
    };

    // Subscribe to the real-time data updates
    this.dataSubscription = this.lineChartService.pollTicketData().subscribe(
      ({ purchased, released, time }) => {
        this.labels.push(time);
        this.purchasedData.push(purchased);
        this.releasedData.push(released);

        // Limit data to the last 10 points
        if (this.labels.length > 10) {
          this.labels.shift();
          this.purchasedData.shift();
          this.releasedData.shift();
        }

        // Update the data and force re-render
        this.data.labels = [...this.labels];
        this.data.datasets[0].data = [...this.releasedData];
        this.data.datasets[1].data = [...this.purchasedData];

        if (this.chart?.chart) {
          this.chart.chart.update(); // Force Chart.js to re-render
        }
      },
      (error) => {
        console.log('Error fetching chart data', error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.dataSubscription) {
      this.dataSubscription.unsubscribe();
    }
  }
}
