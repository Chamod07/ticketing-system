/**
 * Interface representing a log entry.
 */
export interface LogEntry {
  id: number;
  user: string;
  action: string;
  timestamp: string;
}
