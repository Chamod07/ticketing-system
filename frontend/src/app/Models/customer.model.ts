export interface Customer {
  id: string;
  retrievalInterval: number;
  priority?: 'standard' | 'vip';
}
